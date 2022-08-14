#Ciara Trigg
#Collaborated with Thomas Melody and Zack Danchak 

#NUMBER 1

#a
?iris

#b
unique(iris, incomparables = FALSE)
# When run, this function outputs each row within the iris dataset
# and labels each one from 1-150. Thus there are 150 unique rows in
# the dataset

#c
#rownames(iris)[iris$Species == "setosa"]

mean(c(iris$Sepal.Width[1:50])) #setosa
mean(iris$Sepal.Width[51:100]) #versicolor
mean(iris$Sepal.Width[101:150]) #virginica 
#according to the output, setosa has the largest avg sepal width
#and versicolor has the smallest

#d
iris$Species[which.min(iris$Petal.Length)]
#the output of this code indicates that the flower with 
#the smallest petal length is the setosa 

#e
boxplot(Petal.Width~Species, data = iris)
#On a box plot, the two lines above and below the box represent
#the minimum and the maximum of the data. The box represents
#the interquartile range, or the bulk of the data. The dark
#line in the center of the box indicates the median.
#From this boxplot, we can conclude that the flower species
#with the greatest petal width is the virginica, and the species 
#with the smallest petal width is the setosa. Additionally, 
#we can conclude that virginica flowers have the greatest range
#of petal widths judging by the size of the box. 

#NUMBER 2

#B 
lastTerm = 1
thisTerm = 2
sum = 2; 

#continue iterating until the loop generates 4 million terms 
while (thisTerm <= 4000000) {
  nextTerm = lastTerm + thisTerm
  
  if (nextTerm %% 2 == 0) {
    sum = sum + nextTerm
  }
  #shift the identifiers are new terms are generated
  lastTerm = thisTerm
  thisTerm = nextTerm
}
#final sum output 4613732 which, according to Project Euler, is correct

#C
#create a vector with 1 element 
result = c(1)
prevTwo = 1
prevOne = 1

randoFig = function(nBack1, nBack2) {
  if (runif(1) < 0.5) {
    return(nBack1 + nBack2)
  } else {
    return(nBack1 - nBack2)
  }
}

#Loop until 500 
for (i in 2:500) {
  fiboRun = randoFig(prevOne, prevTwo) 
  results = append(results, abs(fiboRun)^(1/i))
  
  prevTwo = prevOne
  prevOne = fiboRun
}

plot(results, xlab='Input', ylab="Output", type="l")
abline(h=1.1319, col="red")


#NUMBER 3

#a 
a = 100
n = 500
A = rep(0,n)
for(t in 1:n){
  if (runif(1)<0.5){
    a = a + 1
  }
  else{
    a = a - 1
  }
  A[t] = a
}
plot(A, type = 'l', xlab = "Time", ylab = "Num of Particles", ylim = c(50,150))

#b
c = 100
m = 1000
C = rep(0,m)
for(t in 1:m){
  if (runif(1)<0.5){
    c = c + 1
  }
  else{
    c = c -1
  }
  C[t] = c
}
hist(C[500:m], main = "Balls in A after t = 500", xlab = "Num of Particles" )

#c
for(i in 1:5){
  d = 100
  D = c(100)
  #f = 1
  while(d > 90 && d < 110) {
    if (runif(1)<0.5){
      d = d + 1
    }
    else{
      d = d - 1
    }
    D = append(D, d)
    #f = f + 1
  }
  if(i == 1){
    plot(D, type = 'l', xlab = "Time", ylab = "Num of Particles", 
    main = "Particles in A", ylim = c(85,115), xlim = c(0, 200),col = rgb(runif(1), runif(1), runif(1)))
    abline(h=c(90,110), col = c("red","red"))
  }
  else{
    lines(D, type = 'l', xlab = "Time", 
    ylab = "Num of Particles", main = "Particles in A", col = rgb(runif(1), runif(1), runif(1)))
  }
}

# D
totalBalls = 100
minBalls = 100
newMin = c(100)
curTime = 0
minTime = c(0)

while(minBalls > 0){
  if(runif(1)<0.5){
    totalBalls = totalBalls + 1
  }
  else{
    totalBalls = totalBalls - 1
  }
  curTime = curTime + 1
  if(totalBalls < minBalls){
    minBalls = totalBalls
    newMin = append(newMin, minBalls)
    minTime = append(minTime, curTime)
  }
  #print(totalBalls)
  #print(minBalls)
}
plot(minTime, newMin, type = 'l', main = "Min particles in A over time", xlab = "Time", ylab = "Num Particles", log = 'x')


#NUMBER 4

#A 
# Since the area of a circle is equal to πr^2, and the area of the square is 
# (2r)^2, the probability of the dart landing in the circle = 0.7854/1, which
# equals 78.5%. 

#B

xCoords = rep(0, 10000)
yCoords = rep(0, 10000)
for (j in 1:10000) {
  xCoords[j] = runif(1, min = -0.5, max = 0.5)
  yCoords[j] = runif(1, min = -0.5, max = 0.5)
}
#determine how many darts landed on the circle
inCircle = 0
for (k in 1:10000) {
  if (xCoords[k]^(2) + yCoords[k]^2 <= (1/2)^2) { 
    inCircle = inCircle + 1
  }
}
# D
# In a test run, of 10000 darts thrown, approximately 78.33% (0.7833) of them 
# landed within the circle. Using the equation for area of a circle:
# A = πr^2, where A = 0.7833 we can solve for the estimated value of pi.
# After doing the calculation, pi is equal to 3.1332, which is similar to the 
# known value of pi, 3.14. 
  



