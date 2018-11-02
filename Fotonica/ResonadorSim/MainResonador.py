import numpy as np
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt

from mpl_toolkits.mplot3d import Axes3D
from Espejo import EspejoResonador

"""
        SIMULACION DE UN RESONADOR OPTICO CONFOCAL CURVO
Se ha simulado un resonador cofocal curvo con espejos circulares estudiando la
intesidad obtenida en los espejos tras un numero de reflexiones. Se ha
comparado la intesidad obtenida utilizando los espejos alineados y con un
movimiento aleatorio de los espejos de 0.25 grado. Para ello se ha tomado el
campo promedio tras tomar 20 medidas con el movimiento aleatorio.

@author Jaimedgp
"""

class RepresentarGraficas():

    def __init__(self, x, y):
        self.x, self.y = x, y

        self.fig = plt.figure()


    def insertarGrafica(self, subplot, campo):
        # Mostrar la imagen rotandola
        self.fig.add_subplot(subplot).imshow(abs(campo), cmap=plt.cm.jet)

        # show the 3D rotated projection
        self.fig.add_subplot(subplot+1, projection='3d').plot_surface(self.x,
                                     self.y, abs(campo), rstride=1, cstride=1,
                                                cmap=plt.cm.jet, linewidth=0,
                                                antialiased=False, shade=False)

    def guardarGraficas(self, nombre):
        self.fig.savefig('./Graficas/'+nombre+'.png', dpi=600)

def guardarDatos(IntAle1, IntAle2, IntMed1, IntMed2, Intensidad1,
                                                        Intensidad2, nombre):

    with open("./Datos/"+nombre+".txt", "w") as fw:
        fw.write("Datos de las intensidades de los espejos.\n")
        fw.write("Espejo 1:\n")
        fw.write("\tIntensidad Media = %s \t Intensidad = %s \t \
                 I_0/I = %s\n" %(IntMed1, Intensidad1, (IntMed1/Intensidad1)))
        fw.write("Espejo 2:\n")
        fw.write("\tIntensidad Media = %s \t Intensidad = %s \t \
                 I_0/I = %s\n" %(IntMed2, Intensidad2, (IntMed2/Intensidad2)))
        fw.write("#########################################################\n")

        fw.write('Espejo 1 \t Espejo2 \n')
        fw.write('__________________________________________\n')
        for i in range(len(IntAle1)):
            fw.write("%s \t %s\n" %((IntAle1[i]),(IntAle2[i])))

def ResonarEnEspejos(numReflexiones, movimiento=True):
    #crear los espejos del resonador con curvatura 0.01 y forma circular
    Espejo1 = EspejoResonador(curvatura=0.01)
    Espejo1.lenteCircular() #utilizar lente circular
    Espejo2 = EspejoResonador(curvatura=0.01)
    Espejo2.lenteCircular() #utilizar lente circular

    #primer rebote
    rebote1 = Espejo1.rebotes(Espejo1.lente)
    rebote1 = np.fft.fftshift(rebote1)
    rebote2 = Espejo2.rebotes(rebote1) # rebote en el espejo2

    for i in range(0, numReflexiones-1):
        if movimiento:
            # El movimiento aleatorio de los dos espejos en un intervalo de 0.25 
            # grado se simula moviendo un solo espejo en un rango de 0.5 grados
            Espejo1.moverEspejoAleatorio(0.5)

        rebote1 = Espejo1.rebotes(rebote2) # rebote en el espejo1

        rebote2 = Espejo2.rebotes(rebote1) # rebote en el espejo2

    return rebote1, rebote2


#########################################
#####       MAIN DEL PROGRAMA       #####
#########################################

numEvents = 20 # numero de eventos con el movimiento aleatorio
numReflexiones = 25 # numero de reflexiones en los espejos

# Arrays para almanezar los campos de los eventos con movimiento aleatorio
Aleatoria1 = [None for i in range(numEvents)] # en espejo 1
Aleatoria2 = [None for i in range(numEvents)] # en espejo 2

Intensidad1, Intensidad2 = ResonarEnEspejos(numReflexiones, False) # resonar sin mover espejos

for i in range(len(Aleatoria1)):

    Aleatoria1[i], Aleatoria2[i] = ResonarEnEspejos(numReflexiones)

AleMed1 = sum(Aleatoria1)/numEvents
AleMed2 = sum(Aleatoria2)/numEvents

#-------------------------------
#   GUARDAR INTENSIDADES
#-------------------------------

IntAle1 = [ abs(np.max(Aleatoria1[i])) for i in range(len(Aleatoria1))]
IntAle2 = [ abs(np.max(Aleatoria2[i])) for i in range(len(Aleatoria2))]

guardarDatos(IntAle1, IntAle2, abs(np.max(AleMed1)), abs(np.max(AleMed2))
             , abs(np.max(Intensidad1)), abs(np.max(Intensidad2)), "DatosEspejo")

#-------------------------------
#   REPRESENTAR LOS CAMPOS
#-------------------------------

# crear los ejes
x = np.arange(0, len(AleMed1))
y = np.arange(0, len(AleMed1))
x, y = np.meshgrid(x, y)

Graficas = RepresentarGraficas(x, y)

# CAMPOS EN ESPEJO 1

Graficas.insertarGrafica(221, Intensidad1) # representar campo sin movimiento

Graficas.insertarGrafica(223, AleMed1) # campo con movimiento

Graficas.guardarGraficas("Espejo1")

# CAMPOS EN ESPEJO 2

Graficas.fig.clf() #borrar subplot actual en caso de existir


Graficas.insertarGrafica(221, Intensidad2) # representar campo sin movimiento

Graficas.insertarGrafica(223, AleMed2) # campo con movimiento

Graficas.guardarGraficas("Espejo2")
