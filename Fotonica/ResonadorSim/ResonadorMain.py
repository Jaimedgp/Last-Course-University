import numpy as np
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt

from Espejo import EspejoResonador
from mpl_toolkits.mplot3d import Axes3D

def guardarDatos(IntEspejo1, IntEspejo2, medInt1, medInt2, Intensidad1, Intensidad2):
    with open('./Datos/Datos.txt', 'w') as fw:
        fw.write('Espejo 1 \t Espejo2 \n')
        for i in range(len(IntEspejo1)):
            fw.write("%s \t %s\n" %(abs(np.max(IntEspejo1[i])),
                                    abs(np.max(IntEspejo2[i]))))

        fw.write('__________________________________________\n')
        fw.write("%s \t %s\n" %(abs(np.max(medInt1)),
                                abs(np.max(medInt2))))
        fw.write('__________________________________________\n')
        fw.write("%s \t %s \n" %(abs(np.max(Intensidad1)),
                                 abs(np.max(Intensidad2))))
        fw.close()
    with open('./Datos/Dif.txt', 'rw') as frw:
        frw.write("%s \t %s \n"
                    %(abs(np.max(Intensidad1))/abs(np.max(medInt1)),
                    abs(np.max(Intensidad2))/abs(np.max(medInt2))))

def mostrarEspejo(x, y, matrix, matrixEj, nombre):
    fig = plt.figure()
    # create the figure
    ax1 = fig.add_subplot(2, 2, 1)
    ax3 = fig.add_subplot(2, 2, 3)
    ax2 = fig.add_subplot(2, 2, 2, projection='3d')
    ax4 = fig.add_subplot(2, 2, 4, projection='3d')

    # Mostrar la imagen rotandola
    ax1.imshow(abs(matrix), cmap=plt.cm.jet)
    # Mostrar la imagen rotandola
    ax3.imshow(abs(matrixEj), cmap=plt.cm.jet)

    # show the 3D rotated projection
    ax2.plot_surface(x, y, abs(matrix), rstride=1, cstride=1, cmap=plt.cm.jet,
                                        linewidth=0, antialiased=False, shade=False)
    # show the 3D rotated projection
    ax4.plot_surface(x, y, abs(matrixEj), rstride=1, cstride=1, cmap=plt.cm.jet,
                                        linewidth=0, antialiased=False, shade=False)

    fig.savefig('./Graficas/'+nombre+'.png', dpi=600)

def ResonarEnEspejos(numReflexiones, movimiento=True):
    #crear los espejos del resonador con curvatura 0.01 y forma circular
    Espejo1 = EspejoResonador(curvatura=0.01)
    Espejo1.lenteCircular()
    Espejo2 = EspejoResonador(curvatura=0.01)
    Espejo2.lenteCircular()

    #primer rebote
    rebote1 = Espejo1.rebotes(Espejo1.lente)
    rebote1 = np.fft.fftshift(rebote1)

    for i in range(0, numReflexiones-1):
        if movimiento:
            Espejo1.moverEspejoAleatorio() #mover espejo aleatoriamente
        rebote1 = Espejo1.rebotes(rebote1)

        rebote2 = Espejo2.rebotes(rebote1)

    return rebote1, rebote2

numEvents = 20

Aleatoria1 = [None for i in range(numEvents)]
Aleatoria2 = [None for i in range(numEvents)]


rebote1, rebote2 = ResonarEnEspejos(50, False)

for i in range(len(Aleatoria1)):

    Aleatoria1[i], Aleatoria2[i] = ResonarEnEspejos(50)

AleMed1 = sum(Aleatoria1)/numEvents
AleMed2 = sum(Aleatoria2)/numEvents

guardarDatos(Aleatoria1, Aleatoria2, AleMed1, AleMed2, rebote1, rebote2)

x = np.arange(0, len(AleMed1))
y = np.arange(0, len(AleMed1))
x, y = np.meshgrid(x, y)

c = mostrarEspejo(x, y, AleMed1, rebote1, 'Espejo1')
d = mostrarEspejo(x, y, AleMed2, rebote2, 'Espejo2')

