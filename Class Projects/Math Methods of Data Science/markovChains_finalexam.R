# FINAL EXAM
# QUESTION 4 PART 2


library(expm)
M = matrix(0, 4, 4)

M[1,2] = (1/3)
M[1,3] = (1/3)
M[1,4] = (1/3)
M[2,1] = 1
M[3,2] = 1
M[4,3] = 1
# Important note: the given Markov chain describes 4 states labelled as 0-3
# In this matrix however, state 0 is represented by location 1, state 1 
# is represented by location 2, and so on. 

nStep = function(mat, numStep, startNode, endNode){
  nStepProbMat = (mat %^% numStep) #generates n step probability matrix
  return(nStepProbMat[startNode,endNode]) # probability of getting from state 
                                          # start+1 to end+1 in n steps
}

iterThreeToTen = nStep(M,7,3,3) #probability of reaching state 2 from state 2 in 7 steps
print(iterThreeToTen)
