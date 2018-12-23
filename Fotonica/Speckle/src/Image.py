from scipy.misc import imread, imresize
import numpy as np
import matplotlib.pyplot as plt
import cmath

class Image():

    def __init__(self, imagenPath):
        # Se abre la imagen
        self.image = imread(imagenPath, False, 'L')
        self.xSize, self.ySize = len(self.image), len(self.image[1])

    def computeComplex(self):
        zeroes = np.zeros((self.xSize, self.ySize), dtype=np.complex_)
        zeroes[:, :] = 1
        self.image = self.image*zeroes

    def resizeImage(self, xSz, ySz):
        self.xSize = xSz
        self.ySize = ySz
        self.image = imresize(self.image, (xSz, ySz))
        self.computeComplex()

    def setImage(self, image):
        self.image = image
        self.xSize, self.ySize = len(self.image), len(self.image[1])

        zeroes = np.zeros((self.xSize, self.ySize), dtype=np.complex_)
        zeroes[:, :] = 1
        self.image = self.image*zeroes

    def aberraciones(self, titl=None, tip=None):
        intervalo = np.deg2rad(90)

        for x in range(0, self.xSize):
            rX = x-(self.xSize/2)
            for y in range(0,self.ySize):
                self.tilt = np.random.randint(-10,10)*(intervalo/10)
                self.tip = np.random.randint(-10,10)*(intervalo/10)

                rY = (self.ySize/2)-y
                r2 = rX**2+rY**2

                self.image[x][y] = (self.image[x][y]*cmath.exp(1j*(self.tip*rX
                                                            + self.tilt*rY)))
