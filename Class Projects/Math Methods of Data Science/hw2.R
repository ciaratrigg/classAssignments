
#NUMBER 5

#a
f = function(x){
  return(x^3)
}

df = function(x){
  return(3*(x^2))
}

grad = function(alpha, iter, init){
  ans = rep(0, iter)
  ans[1] = init
  for (i in 1: (iter - 1)){
    ans[i+1] = ans[i] - alpha * df(ans[i])
  }
  return(ans)
}
pts = grad(.05, 100, .5)
plot(pts, f(pts), xlim = c(-0.5,0.5), ylim = c(-0.01, 0.05))
dom = (-300:300)/100
lines(dom, f(dom), type = 'l')

#In this case, the descent does not end up at a local minimum

#b
pts2 = grad(0.05, 100, -0.5)
plot(pts2, f(pts2), xlim = c(-1.5,0), ylim = c(-2, 0))
lines(dom, f(dom), type = 'l')
# after starting with an initial position of -0.5 instead of 0.5, 
# the values are much more spread out than before where there were
# lots of values clustered in the same area

#c
f2 = function(x){
  return(x[1]^4 + x[2]^4 - (4*x[1]*x[2]) + 1)
}
df2 = function(x){
  return(c(4*x[1]^3 - 4*x[2], 4*x[2]^3 - 4*x[1]))
}

grad2 = function(alpha, iter, init){
  ansx = rep(0, iter)
  ansy = rep(0, iter)
  ansx1 = init[1]
  ansxy = init[2]
  
  ans = init
  
  for(i in 1:(iter - 1)){
    ans = ans - alpha * df2(ans)
    
    ansx[i+1] = ans[1]
    ansy[i+1] = ans[2]
  }
  return (cbind(ansx, ansy))
}

Z = grad2(0.05, 50, c(0.2,0.8))
library(ContourFunctions)
cf_func(f2, xlim = c(-.5*pi, .5*pi), ylim = c(-.5*pi, .5*pi), lines_only = TRUE, 
        afterplotfunc = function(){
          points(Z[,1], Z[,2], pch = 19)
        }
)
# This contour plot shows two local minima at x = -1 and 1
# I used a learning rate of 0.05 and the initial positions 0.2 and 0.8

