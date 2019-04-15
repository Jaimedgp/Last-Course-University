import numpy as np
import matplotlib.pyplot as plt

k = np.linspace(-2*np.pi, 2*np.pi, 100)

M = 2.0

k1 = 1.0
k2 = 2.0

omegaS = (1/M) * ( (k1 + k2) + np.sqrt( (k1 + k2)**2 - 4*k1*k2*(np.sin(k/2)**2)) )
omegaR = (1/M) * ( (k1 + k2) - np.sqrt( (k1 + k2)**2 - 4*k1*k2*(np.sin(k/2)**2)) )

plt.plot(k, omegaS.real, label="$\omega_+$")
plt.plot(k, omegaR.real, label="$\omega_-$")
plt.legend()
plt.show()
