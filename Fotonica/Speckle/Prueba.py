from Espejo import EspejoResonador as Espejo

import numpy as np
import matplotlib.pyplot as plt

lado = 16
sizeBig = 1024

y = 2*lado
alt = int(np.sqrt(3)*lado/2)
x = alt*2

Pupila = Espejo(x, y, lado)
Pupila.lenteHexagonal()

rate = sizeBig/y
sizeX = rate*x

speckel1 = np.zeros((sizeX, sizeBig), dtype=np.complex_)
speckel2 = np.zeros((sizeX, sizeBig), dtype=np.complex_)

numH = (sizeBig-(lado*2))/(lado*3)

yInterval = 3*y/2
for i in range(0, rate):
    xPix = i*x
    for k in range(0, numH+1):
        yPix = yInterval*k

        if (k < 2 and i < 2):
            print xPix, yPix

        Pupila.moverEspejoAleatorio(2)
        Pupilitas = Pupila.lente

        speckel1[xPix:xPix+x, yPix:yPix+y] = Pupilitas[:]

yInterval = 3*(y/2)/2
for i in range(0, rate-1):
    xPix = (2*i+1)*alt
    for k in range(0, numH):
        yPix = yInterval + k*3*y/2


        Pupila.moverEspejoAleatorio(2)
        Pupilitas = Pupila.lente

        speckel2[xPix:xPix+x, yPix:yPix+y] = Pupilitas[:]

speckel = speckel1+speckel2

zeroes = np.zeros((sizeBig, sizeBig), dtype=np.complex_)

zeroes[0:sizeBig-sizeX, :] = 0

zeroes[sizeBig-sizeX: , :] = speckel[:, :]

telescopio = Espejo(sizeBig, sizeBig, sizeBig/4, curvatura=0.01)
telescopio.lenteCircular()
optica = zeroes*telescopio.lente

fourier = np.fft.fft2(optica)
screen = np.fft.fftshift(fourier)

fig = plt.figure()
fig.add_subplot(121).imshow(abs(optica.real), cmap=plt.cm.jet)
fig.add_subplot(122).imshow(abs(screen.real), cmap=plt.cm.jet)
plt.show()
