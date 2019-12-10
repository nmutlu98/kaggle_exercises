#sözlükten random kelime seçimi+
#harf sayısı kadar yıldız bastırmak
#tahmin alıcaz+

#doğruysa -> yıldızı harfe çevir
#yanlışsa -> canı azaltıcaz
import random
def choose_random_word():
  fl = open("words.txt","r")
  words = fl.readlines()
  sira = random.randint(0,len(words)-1)
  return words[sira].strip()
def check_guess(word,guess,starred):
  positions = list() #1 6
  #positions po****o**
  for i in range(0,len(word)):
    if word[i] == guess:
      positions.append(i)
  stars = ""
  for i in range(0,len(word)):
    #a in liste
    if i in positions:
      stars += guess
    else:
      stars += starred[i]
  if len(positions)==0:
    return stars,False
 
  return stars,True
  # kelime harf var mı yok mu?
  #harf varsa yıldızı harfe çevir
def print_stars(word):
  number_of_stars = len(word)
  stars = ""
  for i in range(number_of_stars):
    stars = stars + "*" 
  return stars
    
word = choose_random_word()
starred_word = print_stars(word)
print(starred_word)
lives = 7
while True:

  if lives == 0:
    print("Kaybettiniz...")
    print(word)
    break
  if  not "*" in starred_word:
    print("Tebrikler")
  guess = input("Tahmin: (Çıkmak isterseniz 0 ' a basın.")
  starred_word, word_in = check_guess(word,guess,starred_word)
  if not word_in:
    lives-=1
  print("Canınız :",lives)
  print(starred_word)
  

  if guess == "0":
    break

  

  

