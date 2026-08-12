import subprocess

REPO = r'C:\Users\chase\Desktop\额外植物学\extraBotany'
ORIGIN = 'origin'

def run(args, cwd=REPO):
    return subprocess.run(args, cwd=cwd, capture_output=True, text=True, encoding='utf-8')

# get remaining branches (exclude main)
r = run(['git', 'ls-remote', '--heads', ORIGIN])
branches = []
for line in r.stdout.split('\n'):
    parts = line.split()
    if len(parts) == 2 and parts[1].startswith('refs/heads/') and not parts[1].endswith('main'):
        branches.append(parts[1][len('refs/heads/'):])

# get remaining tags (exclude peeled)
r = run(['git', 'ls-remote', '--tags', ORIGIN])
tags = []
for line in r.stdout.split('\n'):
    parts = line.split()
    if len(parts) == 2 and parts[1].startswith('refs/tags/') and not parts[1].endswith('^{}'):
        tags.append(parts[1][len('refs/tags/'):])

print('remaining branches:', branches)
print('remaining tags count:', len(tags))

for b in branches:
    rr = run(['git', 'push', ORIGIN, '--delete', 'refs/heads/' + b])
    out = (rr.stdout + rr.stderr).strip()
    status = 'OK' if rr.returncode == 0 else 'FAIL: ' + out[-300:]
    print(f'branch {b}: {status}')

for t in tags:
    rr = run(['git', 'push', ORIGIN, '--delete', 'refs/tags/' + t])
    out = (rr.stdout + rr.stderr).strip()
    status = 'OK' if rr.returncode == 0 else 'FAIL: ' + out[-300:]
    print(f'tag {t}: {status}')
