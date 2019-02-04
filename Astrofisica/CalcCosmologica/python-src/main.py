import matplotlib.pyplot as plt
import numpy as np
from CalcCosmologica import CalcCosmologica

z = np.asarray([float(i) for i in range(10)])

calc = CalcCosmologica(0,0.24, 0.76, 71.0)

t = calc.time()
a = calc.a(z)

plt.plot(t, a)
plt.show()
