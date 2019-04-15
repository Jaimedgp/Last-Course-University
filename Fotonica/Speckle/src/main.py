from Speckle import Speckle
from Image import Image
from Espejo import EspejoResonador
from scipy.misc import imrotate
import numpy as np
import matplotlib.pyplot as plt
import cmath

def cutPupil(pupilMesh):
    if len(pupilMesh) > tmnhGrand:
        xstart = len(pupilMesh)/2 - tmnhGrand/2
        pupilMesh = pupilMesh[xstart: xstart+tmnhGrand, : ]
    elif len(pupilMesh) < tmnhGrand:
        pupilMesh = speckle.xtndPupilMesh(pupilMesh)

    if len(pupilMesh[0]) > tmnhGrand:
        ystart = len(pupilMesh[0])/2 - tmnhGrand/2
        pupilMesh = pupilMesh[: , ystart: ystart+tmnhGrand]
    elif len(pupilMesh[0]) < tmnhGrand:
        pupilMesh = speckle.xtndPupilMesh(pupilMesh)

    return pupilMesh

def frenteAleatorio(speckle, rand):

    pupilMesh = speckle.pupilMesh(0, rand) + speckle.pupilMesh(1, rand)

    pupilMesh = cutPupil(pupilMesh)

    if speckle.rate % 2 == 0:
        pupilMesh[speckle.xSz/2:, :] = pupilMesh[: -speckle.xSz/2, :]
    elif speckle.rate % 2 == 0:
        pupilMesh[:, :-speckle.ySz/4] = pupilMesh[:, speckle.ySz/4:]

    pupilMesh = cutPupil(pupilMesh)

    return pupilMesh


#########################################
#####       MAIN DEL PROGRAMA       #####
#########################################

#---------------------------------------
#   Extremo MANCHA AIRY SIN FASE
#---------------------------------------

tmnhPeque = 64
tmnhGrand = 512

ratePupilas = 0.5
telescopeSize = int(tmnhGrand*ratePupilas)
speckle = Speckle(tmnhPeque, telescopeSize, tmnhGrand)

frente = frenteAleatorio(speckle, True)

pupila = EspejoResonador(tmnhGrand, tmnhGrand, tmnhGrand*ratePupilas,0)
pupila.lenteCircular()

obtencion = frente * pupila.lente

imagen = np.fft.fftshift(np.fft.fft2(obtencion))


fig = plt.figure()
fig.add_subplot(121).imshow(obtencion.imag)#, cmap=plt.cm.jet)
fig.add_subplot(122).imshow(abs(imagen), cmap=plt.cm.jet)
plt.show()
fig.savefig("./Hola.png")
