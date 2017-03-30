function [ d, X, Y ] = generatePath(pathType)

R = 20;
laneWidth = 3;

d = [0:0.1:50];
X = d + R;
Y = repmat(laneWidth/2,size(d));                                                                                                                                                                                                                                                   



end

