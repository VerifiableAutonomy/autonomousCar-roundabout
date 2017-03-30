function plotProgress(x1, y1, x2, y2, P, V)
%#codegen

persistent ph1 ph11 ph2 ph22 th th2

if isempty(ph1) || ~isvalid(ph1)
    ph1 = plot(x1, y1, 'r*'); hold on
    ph2 = plot(x2, y2, 'b*'); 
    ph11 = plot(x1, y1, 'r-'); 
    ph22 = plot(x2, y2, 'b-'); hold off;
    th = text(-95,17,['P = ' num2str(P,2)]);
    th2 = text(-95,12,['V = ' num2str(V,2)]);
    axis equal;
    xlim([-100 50]);
    ylim([-20 20]);
    l = legend('Traffic', 'Autonomous Car');
    l.Location = 'SouthWest';
%     drawnow();
%     frame = getframe(1);
%     im = frame2im(frame);
%     [imind,cm] = rgb2ind(im,256);
%     imwrite(imind,cm,'example.gif','gif', 'Loopcount',1);
    return
end

ph1.XData = [x1];
ph1.YData = [y1];
ph2.XData = [x2];
ph2.YData = [y2];

ph11.XData = [ph11.XData x1];
ph11.YData = [ph11.YData y1];
ph22.XData = [ph22.XData x2];
ph22.YData = [ph22.YData y2];

th.String = ['P = ' num2str(P,2)];
th2.String = ['V = ' num2str(V,2)];
% drawnow();
% frame = getframe(1);
% im = frame2im(frame);
% [imind,cm] = rgb2ind(im,256);
% imwrite(imind,cm,'example.gif','gif','WriteMode','append');