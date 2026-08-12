data = open(r'E:\minecraft\cuiguzheng\.minecraft\versions\1.20.1-Forge_47.4.22-test\logs\latest.log', 'rb').read().decode('utf-8', errors='replace')
lines = data.split('\n')
print('total lines:', len(lines))
for i, l in enumerate(lines):
    low = l.lower()
    if 'extrabotany' in low:
        # show loading/error/info lines, skip model warnings
        if any(k in low for k in ['error', 'fatal', 'exception', 'modid', 'loaded', 'dispatch', 'construct', 'success']):
            print(f'{i+1}: {l[:200]}')
    elif any(k in low for k in ['modloading error', 'failed to load mod', 'crash']) and 'extrabotany' not in low:
        pass
