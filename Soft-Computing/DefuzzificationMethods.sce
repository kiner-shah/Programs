x = -10:0.01:10
y = []
for i = x
    if i < -8 then y = [y 0.4 * i + 4];
    elseif i >= -8 & i < -2 then y = [y 0.8];
    elseif i >= -2 & i < 0 then y = [y -0.15 * i + 0.5];
    elseif i >= 0 & i < 2 then y = [y 0.5];
    elseif i >= 2 & i < 4 then y = [y -0.2 * i + 0.9];
    elseif i >= 4 & i < 8 then y = [y 0.1];
    elseif i >= 8 & i < 9 then y = [y -0.1 * i + 0.9];
    elseif i >= 9 then y = [y 0];
    end
end
plot2d(x,y)
//Centroid of area
c = inttrap(x .* y, x) / inttrap(y, x)
plot2d([c,c],[0,0.8])

//Bisector of area
f = x .* y
min = 1000000001
boa = -1
for i=1:2000
    ans = inttrap(x(1:i),y(1:i)) - inttrap(x(i:2000),y(i:2000))
    diff = abs(ans)
    if diff <= min then
        min = diff
        boa = x(i)
    end
end
plot2d([boa,boa],[0,0.8])

//Mean of maximum
indices = []
max_value = max(y(1,:))
for i=1:2000
    if y(i) == max_value then
        indices = [indices x(i)]
    end
end
//disp(indices)
mean = sum(indices) / size(indices,2)
plot2d([mean,mean],[0,0.8])

//Mimimum of maximum
min_val = indices(1)
plot2d([min_val,min_val],[0,0.8])

//Maximum of maximum
max_val = indices(size(indices,2))
plot2d([max_val,max_val],[0,0.8])
