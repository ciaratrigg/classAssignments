#NUMBER 3 PART 3

M = matrix(0, 1000, 1000)

for(i in 1:1000){
  for(j in 1:1000){
    if(i<=j){
      rand = runif(1, min = -1, max = 1)
      rand = rand*sqrt(0.00075)
      M[i,j]= rand
      M[j,i] = rand
    }
  }
}
evals = eigen(M)$values

x = seq(-1,1, by=0.01)
w = (2/pi)*sqrt(1-(x*x))
hist(evals, breaks = 50, prob = TRUE)
lines(x, w, col = 'red', lwd = 5)

# The matrix is symmetric, and since all the entries in the matrix are 
# real values, the eigen values must also be real values.

#NUMBER 4 PART 4

samples=10000
samp = runif(samples)

quant = 1-sqrt(1-samp)

vals = (0:100)/100

pdf=2*(1-vals)

hist(quant, freq=F, xlab ="Quantile")

lines(vals, pdf, col='red')

#NUMBER 5 PART 2
N = matrix(0, 200, 200)
for(i in 1:199){
  N[i,i+1]=1
  N[i+1,i]=1
}

Q = N
for(i in 1:49){
  Q = Q%*%N
}
Q[60,60]
#This outputs 1.264e+14 which is equivalent to (50)
#                                             (25)

#NUMBER 5 PART 3
P = matrix(0, 100, 100)
for(i in 1:99){
  N[i,i+1]=1
  N[i+1,i]=1
}

Q = N
for(i in 1:48){
  Q = Q%*%N
}
Q[60,60]
# This outputs zero which is equivalent to 0, the expected result

#NUMBER 5 PART 5

#Create the matrix 
adjMat = matrix(1,40,40)
for(a in 1:39){
  adjMat[a,a] = 0;
}

for(k in 1:30){
  
}

# Since Kn is a complete graph, each vertex has a path to every other vertex.
# A closed walk of length 1 does not exist
# On a length 2 closed walk, there are 29 possible different paths. 
# On a walk of length 3, there are 29 possible choices for the first step of 
# of the walk, 28 choices for the second step, and 1 choice for the final step.
# With each increase in steps. The amount of possible paths grows at a very fast
# rate, hence the suggestion to use a log plot. 


