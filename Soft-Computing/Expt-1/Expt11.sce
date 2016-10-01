clc
x = [0 1 2 3 4 5 6 7 8 9]
y = []
a = input('First threshold: ')
b = input('Second threshold: ')
c = input('Third threshold: ') 
for i=1:10   
	if (x(i) < a)        
		y(i) = 0
	elseif (x(i) >= a & x(i) < b)
		y(i) = (x(i) - a) / (b - a)
	elseif (x(i) == b)        
		y(i) = 1
	elseif (x(i) >= b & x(i) < c)        
		y(i) = (c - x(i)) / (c - b)
	elseif (x(i) > c)
		y(i) = 0
	end
end
plot2d(x,y)
