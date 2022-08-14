#FINAL EXAM
#QUESTION 3

samp = 10000
x = runif(samp)
quant = (-2)/(x-2)
dlim = (100:500)/100
pdf = 2/(dlim^2)

hist(quant, freq = F, xlab = "Quantile", xlim = c(1,5), prob = TRUE)
lines(dlim, pdf, col = 'red')
