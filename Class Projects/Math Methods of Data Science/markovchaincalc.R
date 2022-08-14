library(expm)
M = matrix(0, 5, 5)

M[1,1] = .5
M[1,3] = .5
M[2,5] = 1
M[3,1] = .5
M[3,2] = .1
M[3,4] = .2
M[3,5] = .2
M[4,4] = .5
M[4,5] = .5
M[5,4] = 1

nStep = function(mat, numStep, startNode, endNode){
  nStepProbMat = (mat %^% numStep) #generates n step probability matrix
  return(nStepProbMat[startNode,endNode]) # probability of getting from state 
  # start to end in n steps
}

prob1 = nStep(M,32,5,4)
print(prob1)