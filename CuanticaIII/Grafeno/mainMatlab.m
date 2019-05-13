e0 = -13.6;
g = 2;

kx = linspace(-2*pi, 2*pi, 100);
ky = linspace(-2*pi, 2*pi, 100);
[kx, ky] = meshgrid(kx, ky);

energiaMas = - g*sqrt(3+2*cos(kx) + 2*cos(kx/2.0 + sqrt(3)*ky/2.0) +...
                                            2*cos(kx/2.0-sqrt(3)*ky/2.0));
energiaMenos = g*sqrt(3+2*cos(kx) + 2*cos(kx/2.0 + sqrt(3)*ky/2.0) +...
                                            2*cos(kx/2.0-sqrt(3)*ky/2.0));

a1 = subplot(121);
	colormap(a1, jet);
	hold on;
		surface(kx, ky, energiaMenos);
		surface(kx, ky, energiaMas);
		shading interp;
	hold off;
a2 = subplot(122);
	colormap(a2, jet);
	imagesc(energiaMenos);