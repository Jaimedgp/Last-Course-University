import numpy as np
import matplotlib.pyplot as plt

c = 299792458 # set speed of ligth [m/s]
G = 6.67408 * (10**(-11.0)) # set gravitational constant [m**3 kg**-1 s**-2]
MS = 1.9891*10**(30.0) # Masa del Sol [kg]
m2pc = 3.24078*(10**(-23.0)) # 1 metro -> 3.24078*(10**(-23)) Mpc

import wget

url = "https://www.gw-openscience.org/GW150914data/P150914/fig2-unfiltered-waveform-H.txt"
Data = wget.download(url)

with open(Data, 'r') as fr:
    time = [] # lista vacia para el tiempo
    strain = [] # lista vacia para el strain
    
    line = fr.readline() # La primera linea corresponde a los titulos
    line = fr.readline()

    while line != '':
        t, s = line.split(" ") # las columnas estan separadas por un espacio en blanco
        time.append(float(t))
        strain.append(float(s)*(10**(-21))) # a la columna del strain se le ha de multiplicar por 10**-21
        line = fr.readline()

#################################
###          GRAFICA          ###
#################################

ax = plt.figure(figsize=(10, 7)).add_subplot(111)
ax.plot(time, strain, 'b')

#---------------------
#      TITULOS        
#---------------------

ax.set_title("Onda Gravitacional", fontsize=20)
ax.set_xlabel("tiempo/s", fontsize=15)
ax.set_ylabel("strain", fontsize=15)
plt.show()

# Se dividen los datos entre variables segun la fase de la que se trate en
# cada variable, la primera lista corresponde al tiempo y la segunda al strain
inspiral = [[], []]
merger = [[], []]
ringdown = [[], []]

# Se reparten los valores de tiempo y strain entre las
# tres fases diferentes con las restricciones anteriores
for i in range(len(time)):
    # zona inspiral t <= 0.40
    if time[i] <= 0.40:
        inspiral[0].append(time[i])
        inspiral[1].append(strain[i])
    # zona merger 0.40 < t < 0.43
    elif time[i] > 0.40 and time[i] < 0.43:
        merger[0].append(time[i])
        merger[1].append(strain[i])
    # zona ringdown t >= 0.43
    elif time[i] >= 0.43:
        ringdown[0].append(time[i])
        ringdown[1].append(strain[i])
        

#################################
###          GRAFICA          ###
#################################

ax = plt.figure(figsize=(10, 7)).add_subplot(111)
# zona inspiral en color azul
ax.plot(inspiral[0], inspiral[1], 'b', label='Inspiral')
# zona merger en color rojo
ax.plot(merger[0], merger[1], 'r', label='Merger')
# zona ringdown en color verde
ax.plot(ringdown[0], ringdown[1], 'g', label='Ringdown')

#---------------------
#      TITULOS        
#---------------------

ax.set_title("Las tres fases de la Fusion", fontsize=20)
ax.set_xlabel("time/s", fontsize=15)
ax.set_ylabel("strain", fontsize=15)
ax.legend()
plt.show()

picos = [] # valores del tiempo para los picos maximos y minimos

# Se obtienen los valores del tiempo para los maximosy los minimos
for i in range(1, len(inspiral[0])-1):
    # maximos
    if (inspiral[1][i] > inspiral[1][i+1] 
                                and inspiral[1][i] > inspiral[1][i-1]):
        picos.append(inspiral[0][i])
    # minimos
    if (inspiral[1][i] < inspiral[1][i+1] 
                                and inspiral[1][i] < inspiral[1][i-1]):
        picos.append(inspiral[0][i])

frq = [] # lista con las frecuencias
tiempo = [] # lista con los tiempos de cada frecuencia

# [maximo, minimo, maximo, minimo, maximo, minimo]
#     |______________|       |---------------|

for i in range(len(picos)-2):
    Periodo = (picos[i+2]-picos[i])
    tiempo.append(picos[i]+Periodo/2)
    frq.append(1/Periodo)
        
print "+-------------------------------------------------+"
print "| Frecuencia en la zona Inspiral: %1.1f +- %1.1f Hz  |" %(np.mean(frq), np.std(frq))
print "+-------------------------------------------------+"

#################################
###          GRAFICA          ###
#################################

ax = plt.figure(figsize=(10, 7)).add_subplot(111)
ax.plot(tiempo, frq, 'o')

#---------------------
#      TITULOS        
#---------------------

ax.set_title("Frecuencia en la zona Inspiral", fontsize=20)
ax.set_xlabel("tiempo/s", fontsize=15)
ax.set_ylabel("$\\nu / Hz$", fontsize=15)
plt.show()

parameters = np.polyfit(tiempo, frq, 8)
timTeo = []
for tim in inspiral[0]:
    if min(tiempo) < tim and tim < max(tiempo):
        timTeo.append(tim)
timTeo = np.asarray(timTeo)
frqTeo = parameters[8]
for i in range(len(parameters)-1):
    frqTeo += parameters[i]*(timTeo**(8-i))

print "Frecuencia en la zona Inspiral: %1.1f +- %1.1f Hz"%(np.mean(frqTeo), np.std(frqTeo))

#################################
###          GRAFICA          ###
#################################

ax = plt.figure(figsize=(10, 7)).add_subplot(111)
ax.plot(tiempo, frq, 'bo')
ax.plot(timTeo, frqTeo, 'r')

#---------------------
#      TITULOS        
#---------------------

ax.set_title("Ajuste de la Frecuencia", fontsize=20)
ax.set_xlabel("tiempo/s", fontsize=15)
ax.set_ylabel("$\\nu / Hz$", fontsize=15)
plt.show()

frqDrv = 0
for i in range(len(parameters)-1):
    frqDrv += (8-i)*parameters[i]*(timTeo**(8-i-1))

#################################
###          GRAFICA          ###
#################################

ax = plt.figure(figsize=(7, 5)).add_subplot(111)
ax.plot(timTeo, frqDrv)

#---------------------
#      TITULOS        
#---------------------

ax.set_title("Derivada de la Frecuencia", fontsize=20)
ax.set_xlabel("tiempo/s", fontsize=15)
ax.set_ylabel("$\\frac{\\mathrm{d} \\nu}{\\mathrm{d} t} / Hz^2$", fontsize=15)
plt.show()

MasaC = ((frqDrv*5.0*(c**5.0)) / ((frqTeo**(11/3.0)) * 96.0 * (np.pi**(8.0/3)) * (G**(5.0/3))))**(3/5.0)
MasaCMed = ((np.mean(frqDrv)*5.0*(c**5.0)) / ((np.mean(frqTeo)**(11/3.0)) * 96.0 * (np.pi**(8.0/3)) * (G**(5.0/3))))**(3/5.0)
    
McTh = ((29.2*36.2*(MS**2))**(3/5.0)) / (((29.1+36.2)*MS)**(1/5.0))

print "+-------------------+------------------------------+"
print "| Mchirp obtenida:  |  (%1.1f +- %1.1f) Masas solares |" %(np.mean(MasaC)/MS, np.std(MasaC)/MS)
print "| Mchirp Teorica:   |  %.5f      Massa Solares |" %(McTh/MS) 
print "+-------------------+------------------------------+"
    
#################################
###          GRAFICA          ###
#################################

ax = plt.figure(figsize=(5, 3.5)).add_subplot(111)
ax.plot(frqTeo, MasaC)

#---------------------
#      TITULOS        
#---------------------

ax.set_title("Masa de Chirp", fontsize=20)
ax.set_xlabel("tiempo/s", fontsize=15)
ax.set_ylabel("$M_c / M_{\odot}$", fontsize=15)
plt.show()

indexMinH = inspiral[0].index(min(timTeo))
indexMaxH = inspiral[0].index(max(timTeo))+1
h = inspiral[1][indexMinH:indexMaxH]
h = np.asarray(h)

Dl = (5*2*(c*m2pc)*frqDrv)/(96*(np.pi**2.0)*np.mean(abs(h))*(frqTeo**3.0))

print "+----------------------------------------------+"
print "|       Distancia DL:    (%1.0f +- %1.0f) Mpc      |" %(np.mean(Dl), np.std(Dl))
print "+----------------------------------------------+"

#################################
###          GRAFICA          ###
#################################

ax = plt.figure(figsize=(5, 3.5)).add_subplot(111)
ax.plot(timTeo, Dl, 'r')

#---------------------
#      TITULOS        
#---------------------

ax.set_title("Distancia Luminica", fontsize=20)
ax.set_xlabel("tiempo/s", fontsize=15)
ax.set_ylabel("$D_L / Mpc$", fontsize=15)
plt.show()

Dl = (5*(c*m2pc)*frqDrv)/(96*(np.pi**2.0)*np.mean(abs(h))*(frqTeo**3.0))

print "+----------------------------------------------+"
print "|       Distancia DL:    (%1.0f +- %1.0f) Mpc       |" %(np.mean(Dl), np.std(Dl))
print "+----------------------------------------------+"