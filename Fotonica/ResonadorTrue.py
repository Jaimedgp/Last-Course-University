import numpy as np
import matplotlib.pyplot as plt
import threading

from Pupila import EspejoResonador
from mpl_toolkits.mplot3d import Axes3D
#from scipy.misc import imread, imresize, imrotate

def mostrarEspejo(x, y, matrix):
    # create the figure
    fig = plt.figure(figsize=plt.figaspect(0.5))
    ax1 = fig.add_subplot(1, 2, 1)
    ax2 = fig.add_subplot(1, 2, 2, projection='3d')

    # Mostrar la imagen rotandola
    ax1.imshow(abs(matrix), cmap=plt.cm.jet)#, interpolation='nearest',
                #origin='lower', extent=[0,1,0,1])

    # show the 3D rotated projection
    ax2.plot_surface(x, y, abs(matrix), rstride=1, cstride=1, cmap=plt.cm.jet,
                                        linewidth=0, antialiased=False, shade=False)


Espejo1 = EspejoResonador(curvatura=0.01)
Espejo1.lenteCircular()
Espejo2 = EspejoResonador(curvatura=0.012)
Espejo2.lenteCuadrada()

fig = plt.figure()
a = fig.add_subplot(121)
b = fig.add_subplot(122)
a.imshow(abs(Espejo1.lente))
b.imshow(abs(Espejo2.lente))

M1 = Espejo1.lente
for i in range(0, 5):
    rebote1 = Espejo1.rebotes(M1)
    if i == 0:
        rebote1 = np.fft.fftshift(rebote1)

    M1 = Espejo2.rebotes(rebote1)

x = np.arange(0, len(M1))
y = np.arange(0, len(M1))
x, y = np.meshgrid(x, y)

c = mostrarEspejo(x, y, rebote1)
d = mostrarEspejo(x, y, M1)
plt.show()
