x = [0 1 2 3 4 5 6 7 8 9]
y = []
a = input('First threshold: ')
b = input('Second threshold: ')
for i=1:10
	if (x(i) <= a)
		y(i) = 1
	elseif (x(i) > a & x(i) < b)
		y(i) = (b - x(i)) / (b - a)
	elseif (x(i) >= b)
		y(i) = 0
	end
end
plot2d(x,y)
