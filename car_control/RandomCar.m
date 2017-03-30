function [ x, y, t ] = RandomCar( )

v1 = normrnd(15,1);
d1 = normrnd(50,0.25);
dd = d1 - 100;
psi2 = -deg2rad(75);
psi_dot2 = -normrnd(pi/2/2,pi/2/45);
v2 = 2.5 * abs(psi_dot2);
psi3 = pi/2 + deg2rad(68);
psi_dot3 = normrnd(pi/2/2,pi/2/45);
v3 = 12.9 * psi_dot3;
psi4 = pi/2;
psi_dot4 = -normrnd(pi/2/2,pi/2/45);
v4 = 4 * abs(psi_dot4);

x(1) = 0;
y(1) = 0;
t(1) = 0;
dt = 0.01;
d = 0;
psi = 0;

v = v1;
state = 1;

for i=2:10000
    x(i) = x(i-1) + v*dt*cos(psi);
    y(i) = y(i-1) - v*dt*sin(psi);
    t(i) = t(i-1) + (i-1)*dt;
    d = d + v*dt;
    if d > d1 && state <= 2
        state = 2;
        if t(1) == 0
            t(1) = i*dt;
            idx(1) = i;
        end
        v = v2;
        psi = psi + psi_dot2*dt;
        if psi < psi2
            t(2) = i*dt;
            idx(2) = i;
            state = 3;
            v = v3;
        end
    end
    if state == 3
        psi = psi + psi_dot3*dt;
        if psi > psi3
            t(3) = i*dt;
            idx(3) = i;
            state = 4;
            v = v4;
        end
    end
    if state == 4
        psi = psi + psi_dot4*dt;
    end
    if state == 4 && psi < psi4
        t(4) = i*dt;
        idx(4) = i;
        break;
    end
end

end

