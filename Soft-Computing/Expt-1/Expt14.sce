x = [0 1 2 3 4 5 6 7 8 9]
y = []
a = input('First threshold: ')
b = input('Second threshold: ')
c = input('Third threshold: ')
d = input('Fourth threshold: ')
for i=1:10
	if (x(i) <= a)
		y(i) = 0
	elseif (x(i) > a & x(i) < b)
		y(i) = (x(i) - a) / (b - a)
	elseif (x(i) >= b & x(i) <= c)
		y(i) = 1
	elseif (x(i) > c & x(i) < d)
		y(i) = (d - x(i)) / (d - c)
	elseif (x(i) >= d)
		y(i) = 0
	end
end
plot2d(x,y)
