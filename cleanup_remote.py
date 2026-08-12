import subprocess
import sys

ORIGIN = 'origin'
REPO = r'C:\Users\chase\Desktop\额外植物学\extraBotany'

def run(args, cwd=REPO):
    r = subprocess.run(args, cwd=cwd, capture_output=True, text=True, encoding='utf-8')
    return r

# 1. get remote branch names
r = run(['git', 'ls-remote', '--heads', ORIGIN])
branches = []
for line in r.stdout.split('\n'):
    parts = line.split()
    if len(parts) == 2:
        ref = parts[1]
        if ref.startswith('refs/heads/'):
            branches.append(ref[len('refs/heads/'):])
print('branches to delete:', len(branches))

# 2. get remote tag names (exclude peeled ^{})
r = run(['git', 'ls-remote', '--tags', ORIGIN])
tags = []
for line in r.stdout.split('\n'):
    parts = line.split()
    if len(parts) == 2:
        ref = parts[1]
        if ref.startswith('refs/tags/') and not ref.endswith('^{}'):
            tags.append(ref[len('refs/tags/'):])
print('tags to delete:', len(tags))

# 3. delete branches (git push origin --delete b1 b2 ...)
# batch to avoid command-line length limits
def delete(refs, kind):
    for i in range(0, len(refs), 30):
        batch = refs[i:i+30]
        r = run(['git', 'push', ORIGIN, '--delete'] + batch)
        out = (r.stdout + r.stderr).strip()
        ok = 'deleted' in out or 'already' in out.lower() or r.returncode == 0
        print(f'{kind} batch {i//30}: rc={r.returncode} | {out[-200:]}')
        if r.returncode != 0:
            print('  FULL OUTPUT:', out[-500:])

delete(branches, 'branch')
delete(tags, 'tag')

print('DONE deleting')
