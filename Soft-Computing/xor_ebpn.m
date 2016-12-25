%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% XOR Problem using Error Back Propagation Network
% This code leads to 100% accuracy in 744 epochs with learning rate 1.0
% Code written by Kiner Shah, 25th December 2016
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Define parameters
x = [0 0 ; 0 1; 1 0; 1 1];
% x = [0 1];
t = [0; 1; 1; 0];
% t = [1];
b1 = 0.3; b2 = 0.15; b3 = 0.5;
w = [0.05; 0.1; 0.1; 0.1];
v = [0.1; -0.1]; eta = 1; alpha = 1;
for i=1:744
    fprintf('EPOCH %d\n', i);
    fprintf('Target\tActual\n');
    for j=1:size(x, 1)
        % Calculate net and output for hidden layer j
        net1j = x(j, 1) * w(1) + x(j, 2) * w(3) + b1;
        net2j = x(j, 1) * w(2) + x(j, 2) * w(4) + b2;
        out1j = 1 / (1 + exp(-net1j));
        out2j = 1 / (1 + exp(-net2j));
        % Calculate net and output for output layer k
        netk = [out1j out2j] * v + b3;
        outk = 1 / (1 + exp(-netk));
        % Calculate error
        err = sum((t(j, 1) - outk) .^ 2);
        % Propagate error to hidden - output layer weights
        deltak = (t(j, 1) - outk') .* outk .* (1 - outk);
        dw1jk = eta * deltak * out1j;
        dw2jk = eta * deltak * out2j;
        v = v + [dw1jk; dw2jk];
        b3 = b3 + eta * deltak;
        % Propagate error to input - hidden layer weights
        delta1j = (deltak * v(1)) * out1j * (1 - out1j);
        delta2j = (deltak * v(2)) * out2j * (1 - out2j);
        dw1ij = alpha * delta1j * x(j, 1);
        dw3ij = alpha * delta1j * x(j, 2);
        dw2ij = alpha * delta2j * x(j, 1);
        dw4ij = alpha * delta2j * x(j, 2);
        w = w + [dw1ij; dw2ij; dw3ij; dw4ij];
        b1 = b1 + alpha * delta1j;
        b2 = b2 + alpha * delta2j;
        fprintf('%d\t\t%f\n', t(j, 1), round(outk));
    end
    fprintf('\n');
end
% Special thanks to the following post:
% http://stackoverflow.com/questions/39663730/backpropagation-not-working-for-xor
