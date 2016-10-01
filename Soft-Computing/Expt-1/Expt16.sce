x = [0 1 2 3 4 5 6 7 8 9]
a = input('Enter first threshold: ')
b = input('Enter second threshold: ')
c = input('Enter third threshold: ')
y = []
for i=1:10
    y(i) = 1/(1 + abs((x(i) - c)/a)^(2*b))
end
plot2d(x,y)
