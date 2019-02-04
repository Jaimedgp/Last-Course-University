classdef CalcCalculator
	
	properties
		OMat = 0.24;
		OA = 0.76;
		ORd = 0;
		H0 = 71.0;

		OK = 0;
	end

	methods

		function self = CalcCalculator(self)
			self.OK = 1-(self.OMat+self.OA+self.ORd);
			
		end
		function e = E(self, z)
			e = sqrt(self.OA + self.OMat*(1+z)^3 + self.ORd*(1+z)^4 + self.OK*(1+z)^2);
		end

		function tiempo = t(self, z)
			tiempo = zeros(1, length(z));
			
			for i=1:length(z)
				integ = quad(@(x) 1/((1+x)*self.E(x)), 0, z(i));

				tiempo(i) = (1/self.H0)*integ;
			end
		end

		function a = a(self, z)
			a = 1/(1+z);
		end

	end

end
