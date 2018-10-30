import math
import cmath
import numpy as np

class EspejoResonador():

    def __init__(self, tamanhoX=600, tamanhoY=600, diametro=120, curvatura=0,
                 tilt=0, tip=0):

        self.diamtr = diametro
        self.crv = curvatura
        self.tilt = tilt
        self.tip = tip
        self.sizeX = tamanhoX
        self.sizeY = tamanhoY

        self.lente = np.zeros((self.sizeX, self.sizeY), dtype=np.complex_)


        for x in range(0, self.sizeX):
            rX = self.sizeX-x
            for y in range(0,self.sizeY):
                rY = y-self.sizeY
                r2 = rX**2+rY**2
                if r2 > (self.diamtr/2)**2:
                    self.lente[x][y] = cmath.exp(1j*self.crv*r2)
                if r2 == (self.diamtr/2)**2:
                    self.lente[x][y] = 0.5*cmath.exp(1j*self.crv*r2)

    def rebotes(self, imagen):
        M2 = imagen*self.lente
        reflec = np.fft.fft2(M2)

        return reflec


