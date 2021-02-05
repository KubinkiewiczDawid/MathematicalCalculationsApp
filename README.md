# MathematicalCalculationsApp

Minimalna wersja JDK - 14

Program wykonujący operacje matematyczne w zależności od podanych danych wejściowych.

W pierwszej kolejności należy podać dane wejściowe oddzielając je od siebie spacją.
Dana wejściowa może być: liczba rzeczywista, wektor lub macierz.

Instrukcje dotyczące wprowadzania danych wejściowych:

  1. Wprowadzanie liczby rzeczywistej
  
    Aby wprowadzić do programu liczbę rzeczywistą należy wpisać liczbę rzeczywistą,
    UWAGA!
      przy wpisywaniu liczby ujemnej nie należy po znaku minusa wpisywać spacji

  2. Wprowadzanie macierzy 

    Aby wprowadzić do programu macierz należy trzymać się odpowiedniego schematu:

        [0,0,0/2,1,5/6,1,2]
  
    powyższe dane wejściowe przedstawiają następującą macierz:
  
          | 0 0 0 |
          | 2 1 5 |
          | 6 1 2 |

    1.1. Macierz rozpoczyna się i kończy nawiasem kwadratowym []
    1.2. Przecinki oddzielają wartości od siebie definiując tym samym ilość kolumn
    1.3. Prawe ukośniki oddzielają od siebie kolumny, definiując tym samym ilość wierszy
    1.4. Zamiast 0 można wprowadzić sam przecinek (wzór na powyższą macierz wyglądał by następująco - [,,/2,1,5/6,1,2]), program zastąpu puste miejsca zerami
 
  3. Wprowadzenie wektora
  
    Aby wprowadzić do programu wektor należy trzymać się odpowiedniego schematu:
    
        [0,2,5]
    
    powyższe dane wejściowe przedstawiają następujący wektor:
    
          [0 2 5]
          
    1.1. Wektor rozpoczyna się i kończy nawiasem kwadratowym []
    1.2. Przecinki oddzielają wartości od siebie definiując tym samym ilość kolumn
    1.3. Zamiast 0 można wprowadzić sam przecinek (wzór na powyższy wektor wyglądał by następująco - [,2,5]), program zastąpu puste miejsca zerami
