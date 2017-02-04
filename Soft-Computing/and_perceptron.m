x = [1 1; 1 -1; -1 1; -1 -1];
b = 0;
w = [0.2 0.5];
t = [1 -1 -1 -1];
c = 3;
for i=1:c
    fprintf('Iteration %d\n', i);
    for j=1:4
        net = x(j, 1) * w(1) + x(j, 2) * w(2) + b;
        out = 0;
        if net > 0
            out = 1;
        elseif net == 0
            out = 0;
        elseif net < 0
            out = -1;
        end
        if out ~= t(j)
            w(1) = w(1) + t(j) * x(j, 1);
            w(2) = w(2) + t(j) * x(j, 2);
            b = b + t(j) * 1;
        end
        fprintf('For input %d, the output obtained was: %d\n', j, out);
    end
end