import random as rd
import matplotlib.pyplot as plt

dropsInside = 0.0
drops = 6*10**6

dropsInside = [[], []]
dropsOutside = [[], []]

for i in range(0, drops):
	x = rd.random()
	y = rd.random()

	if x**2+y**2 < 1:
		dropsInside[0].append(x)
		dropsInside[1].append(y)
	else:
		dropsOutside[0].append(x)
		dropsOutside[1].append(y)

inSide = len(dropsInside[0])
print 4.0*inSide/drops

plt.plot(dropsInside[0], dropsInside[1], 'ro')
plt.plot(dropsOutside[0], dropsOutside[1], 'bo')
plt.show()