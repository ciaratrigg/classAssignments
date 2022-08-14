#Problem 6: Vacuumba in a hallway

#NUMBER 2 PART C

M = matrix(0,3,3)

M[1,1] = -1
M[1,2] = 9/10
M[1,3] = 0
M[2,1] = -1/2
M[2,2] = 1
M[2,3] = -1/2
M[3,1] = 0
M[3,2] = -1/2
M[3,3] = 1

v = c(0, 0, 1/2)

soln = solve(M, v)
print(soln)

#NUMBER 2 PART D
N = matrix(0,19,19)
N[1,1] = -1
N[1,2] = 9/10
N[1,3] = 0
N[19,18] = -1/2
N[19,19] = 1

for(i in 2:18){
  N[i,i-1] = -1/2
  N[i,i] = 1
  N[i,i+1] = -1/2
}

vect = rep(0,19)
vect[19] = 1/2

#NUMBER 2 PART E
soln2 = solve(N, vect)

print(soln2)


#NUMBER 2 PART 3: EXTRA CREDIT
A = matrix(0,19,19)
A[1,1] = -1
A[1,2] = 9/10
A[19,18] = 1/2
A[19,19] = -1

for(j in 2:18){
  A[j,j-1] = 1/2
  A[j,j] = -1
  A[j,j+1] = 1/2
}
#print(A)

vect2 = rep(0,19)
vect2[19] = -1/2
soln3 = solve(A, vect2)

print(soln3)

