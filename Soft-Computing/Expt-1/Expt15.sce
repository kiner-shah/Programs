x = [0 1 2 3 4 5 6 7 8 9]
a = sum(x)/10;
b = sum((x - a).^2)/10;
y = []
for i=1:10
    y(i) = exp(-0.5 * ((x(i) - a)/b)^2)
end
plot2d(x,y)
