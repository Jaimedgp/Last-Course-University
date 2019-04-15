import numpy as np
import matplotlib.pyplot as plt

k = np.linspace(-2*np.pi, 2*np.pi, 100)

M = 4.0 *10**(-23)

k1 = 9.0 *10**(3)

omega = (2/M) * k1 * ( (1-np.cos(k)) + (1/50.0)*(1-np.cos(2*k)) )
omegaS = (2/M) * k1 * ( (1-np.cos(k)) + (1/2.0)*(1-np.cos(2*k)) )

plt.plot(k, np.sqrt(omega), label="$k_2 = \\frac{k_1}{50}$")
plt.plot(k, np.sqrt(omegaS), label="$k_2 = \\frac{k_1}{22}$")
plt.legend()
plt.show()
