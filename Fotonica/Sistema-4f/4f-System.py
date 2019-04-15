from SistemaOp import SistemaOptico
import numpy as np
import matplotlib.pyplot as plt
from scipy.misc import imrotate

Sistema = SistemaOptico("/home/jaimedgp/Desktop/logo.png")#"Imagen1.jpg")

f, plots = plt.subplots(2, 3)
plots[0,0].imshow(Sistema.image, cmap="gray")
plots[1,0].imshow(Sistema.image, cmap="gray")

plots[0,1].imshow(imrotate(abs(Sistema.filtroPasoAlto()), 180), cmap="gray")
plots[1,1].imshow(imrotate(abs(Sistema.filtroPasoBajo()), 180), cmap="gray")

imagePA = Sistema.lenteCoFocal()*Sistema.filtroPasoAlto()
imagePB = Sistema.lenteCoFocal()*Sistema.filtroPasoBajo()

plots[0,2].imshow(imrotate(abs(np.fft.fft2(imagePA)), 180), cmap="gray")
plots[1,2].imshow(imrotate(abs(np.fft.fft2(imagePB)), 180), cmap="gray")

plt.show()

image = 1 - abs(np.fft.fft2(imagePA))

plt.imshow(imrotate(image, 180), cmap="gray")
plt.show()

#plt.imshow(imrotate(abs(np.fft.fft2(imagePA+imagePB)), 180), cmap="gray")
#plt.show()
