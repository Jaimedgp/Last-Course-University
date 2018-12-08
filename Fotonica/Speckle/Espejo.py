import random
import cmath
import numpy as np

class EspejoResonador():

    def __init__(self, tamanhoX=600, tamanhoY=600, diametro=100.0, curvatura=0,
                 tilt=0.0, tip=0.0):

        self.diamtr = diametro
        self.crv = curvatura
        self.tilt = tilt
        self.tip = tip
        self.sizeX = tamanhoX
        self.sizeY = tamanhoY

        self.lente = np.zeros((self.sizeX, self.sizeY), dtype=np.complex_)

    def lenteCircular(self):
        for x in range(0, self.sizeX):
            rX = x-(self.sizeX/2)
            for y in range(0,self.sizeY):
                rY = (self.sizeY/2)-y
                r2 = rX**2+rY**2
                if r2 < (self.diamtr/2)**2:
                    self.lente[x][y] = (cmath.exp(1j*self.crv*r2)*cmath.exp(1j*
                                                (self.tip*rX + self.tilt*rY)))

                if r2 == (self.diamtr/2)**2:
                    self.lente[x][y] = (0.5*cmath.exp(1j*self.crv*r2)*
                                    cmath.exp(1j*(self.tip*rX + self.tilt*rY)))

    def lenteCuadrada(self):
        for x in range(0, self.sizeX):
            rX = x-(self.sizeX/2)
            for y in range(0,self.sizeY):
                rY = (self.sizeY/2)-y
                r2 = rX**2+rY**2

                if abs(rX) < self.diamtr and abs(rY) < self.diamtr:
                    self.lente[x][y] = cmath.exp(1j*self.crv*r2)
                elif ((abs(rX) < self.diamtr or abs(rY) < self.diamtr)
                        and (abs(rX) == self.diamtr or abs(rY) == self.diamtr)):

                        self.lente[x][y] = 0.5*cmath.exp(1j*self.crv*r2)


    def rebotes(self, imagen):
        M2 = imagen*self.lente
        reflec = np.fft.fft2(M2)

        return reflec

    def moverEspejoAleatorio(self, rango):
        intervalo = np.deg2rad(rango)

        self.tilt += np.random.randint(-10,10)*(intervalo/10)
        while self.tilt > intervalo:
            self.tilt = self.tilt-2*intervalo
        while self.tilt < -intervalo:
            self.tilt = 2*intervalo - self.tilt

        self.tip += np.random.randint(-10,10)*(intervalo/10)
        while self.tip > intervalo:
            self.tip = self.tip-2*intervalo
        while self.tip < -intervalo:
            self.tip = 2*intervalo - self.tip

        self.lenteCircular()
