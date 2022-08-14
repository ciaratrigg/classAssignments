# FINAL EXAM
# QUESTION 1 PART B
f = function(x){
  return(x[1]*x[2])
}
df = function(x){
  return(c(x[2],x[1]))
}

grad = function(alpha, iter, init){
  ansx = rep(0, iter)
  ansy = rep(0, iter)
  ansx1 = init[1]
  ansxy = init[2]
  
  ans = init
  
  for(i in 1:(iter - 1)){
    ans = ans - alpha * df(ans)
    
    ansx[i+1] = ans[1]
    ansy[i+1] = ans[2]
  }
  return (cbind(ansx, ansy))
}

Z = grad(0.2, 50, c(2,0))

library(ContourFunctions)
cf_func(f, xlim = c(-2,4), ylim = c(-5,2), lines_only = TRUE, 
        afterplotfunc = function(){
          points(Z[,1], Z[,2], pch = 19)
        }
)
Z2 = grad(0.2, 50, c(1,-1))
cf_func(f, xlim = c(-2,4), ylim = c(-5,2), lines_only = TRUE, 
        afterplotfunc = function(){
          points(Z2[,1], Z2[,2], pch = 19)
        }
)