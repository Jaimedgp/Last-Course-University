import numpy as np
import matplotlib.pyplot as plt
import threading

from Espejo import EspejoResonador
from mpl_toolkits.mplot3d import Axes3D

def mostrarEspejo(x, y, matrix, fig, i):
    # create the figure
    ax1 = fig.add_subplot(3, 2, i)
    ax2 = fig.add_subplot(3, 2, i+1, projection='3d')

    # Mostrar la imagen rotandola
    ax1.imshow(abs(matrix), cmap=plt.cm.jet)#, interpolation='nearest',
                #origin='lower', extent=[0,1,0,1])

    # show the 3D rotated projection
    ax2.plot_surface(x, y, abs(matrix), rstride=1, cstride=1, cmap=plt.cm.jet,
                                        linewidth=0, antialiased=False, shade=False)


Espejo1 = EspejoResonador(curvatura=0.01)
Espejo1.lenteCircular()
Espejo2 = EspejoResonador(curvatura=0.01)
Espejo2.lenteCircular()

fig = plt.figure()
a = fig.add_subplot(321)
b = fig.add_subplot(322)
a.imshow(abs(Espejo1.lente))
b.imshow(abs(Espejo2.lente))

M1 = Espejo1.lente
for i in range(0, 10):
    rebote1 = Espejo1.rebotes(M1)
    if i == 0:
        rebote1 = np.fft.fftshift(rebote1)
    #Espejo1.moverEspejoAleatorio()

    M1 = Espejo2.rebotes(rebote1)
    #Espejo2.moverEspejoAleatorio()

print 'Curvatura final espejo 1: '
print '\t'+'tilt = %.2f' %(Espejo1.tilt)
print '\t'+'tip = %.2f' %(Espejo1.tip)

print 'Curvatura final espejo 2: '
print '\t'+'tilt = %.2f' %(Espejo2.tilt)
print '\t'+'tip = %.2f' %(Espejo2.tip)

x = np.arange(0, len(M1))
y = np.arange(0, len(M1))
x, y = np.meshgrid(x, y)

c = mostrarEspejo(x, y, rebote1, fig, 3)
d = mostrarEspejo(x, y, M1, fig, 5)
plt.show()
