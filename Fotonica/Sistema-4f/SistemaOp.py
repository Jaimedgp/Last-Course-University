from scipy.misc import imread
import numpy as np
import matplotlib.pyplot as plt

class SistemaOptico():

	def __init__(self, imagenPath):
		# Se abre la imagen
		self.image = imread(imagenPath, False, 'L')
		self.xSize, self.ySize = len(self.image), len(self.image[1])


	def lenteCoFocal(self, imagenToProcess=0):
		if imagenToProcess == 0:
			imagenToProcess = self.image
		# Primera lente
		fourier = np.fft.fft2(imagenToProcess)
		return np.fft.fftshift(fourier)

	def filtroPasoAlto(self, imagenToProcess=0):
		if imagenToProcess == 0:
			imagenToProcess = self.image
		filtro =  np.zeros((self.xSize, self.ySize))
		CeroX = self.xSize/2
		CeroY = self.ySize/2
		for x in range(0,self.xSize):
			rX = CeroX-x
			for y in range(0,self.ySize):
				rY = y-CeroY
				if rX**2+rY**2 >= 10**2:
					filtro[x][y] = 1
		return filtro

	def filtroPasoBajo(self, imagenToProcess=0):
		if imagenToProcess == 0:
			imagenToProcess = self.image
		filtro =  np.zeros((self.xSize, self.ySize))
		CeroX = self.xSize/2
		CeroY = self.ySize/2
		for x in range(0,self.xSize):
			rX = CeroX-x
			for y in range(0,self.ySize):
				rY = y-CeroY
				if rX**2+rY**2 <= 50**2:
					filtro[x][y] = 1
		return filtro
