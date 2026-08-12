import os
from collections import defaultdict

SRC = r'C:\Users\chase\Desktop\额外植物学\extra-botany-1.16\src\main\java\com\meteor\extrabotany'
TGT = r'C:\Users\chase\Desktop\额外植物学\extraBotany\src\main\java\com\meteor\extrabotany'
SEP = chr(92)  # backslash

def rel_files(root):
    out = set()
    for dp, _, fs in os.walk(root):
        for f in fs:
            if f.endswith('.java'):
                full = os.path.join(dp, f)
                out.add(os.path.relpath(full, root).replace(SEP, '/'))
    return out

src = rel_files(SRC)
tgt = rel_files(TGT)
missing = sorted(src - tgt)
print('TOTAL missing files:', len(missing))
groups = defaultdict(list)
for m in missing:
    parts = m.split('/')
    groups[chr(47).join(parts[:2])].append(m)
for g in sorted(groups):
    print('\n== %s (%d) ==' % (g, len(groups[g])))
    for m in sorted(groups[g]):
        print('  ', m)
