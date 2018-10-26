from Resonador import SistemaOptico
import numpy as np
import matplotlib.pyplot as plt
from scipy.misc import imrotate

Sistema = SistemaOptico("Imagen2.jpg")

f, plots = plt.subplots(2, 3)
plots[0,0].imshow(Sistema.image, cmap="gray")
plots[1,0].imshow(Sistema.image, cmap="gray")

plots[0,1].imshow(imrotate(abs(Sistema.filtroPasoAlto()), 180), cmap="gray")
plots[1,1].imshow(imrotate(abs(Sistema.filtroPasoBajo()), 180), cmap="gray")

imagePA = np.fft.fft2(Sistema.lenteCoFocal()*Sistema.filtroPasoAlto())
imagePB = np.fft.fft2(Sistema.lenteCoFocal()*Sistema.filtroPasoBajo())

plots[0,2].imshow(imrotate(abs(imagePA), 180), cmap="gray")
plots[1,2].imshow(imrotate(abs(imagePB), 180), cmap="gray")

plt.show()
