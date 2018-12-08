from Espejo import EspejoResonador as Espejo

import numpy as np
import matplotlib.pyplot as plt

sizeSmall = 16
sizeBig = 512

Pupila = Espejo(sizeSmall, sizeSmall, sizeSmall)

speckel = np.zeros((sizeBig, sizeBig), dtype=np.complex_)

rate = sizeBig/sizeSmall

for i in range(0, rate):
    Xpix = sizeSmall*i
    for k in range(0, rate):
        Ypix = sizeSmall*k
        Pupila.moverEspejoAleatorio(2)
        Pupilitas = Pupila.lente
        speckel[Xpix:Xpix+sizeSmall,Ypix:Ypix+sizeSmall] = Pupilitas[:]

Lente = Espejo(sizeBig, sizeBig, 128)
Lente.lenteCircular()
optica = Lente.lente * speckel

fourier = np.fft.fft2(optica)
screen = np.fft.fftshift(fourier)

fig = plt.figure()
fig.add_subplot(121).imshow(abs(optica.imag), cmap=plt.cm.jet)
fig.add_subplot(122).imshow(abs(screen.imag), cmap=plt.cm.jet)
plt.show()
