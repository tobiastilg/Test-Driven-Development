# Test-Driven-Development

Das Kernkonzept des Test-Driven-Developments ist das Erstellen von Softwaretests **vor** dem eigentlichen Erstellen der zu testenden Komponente. Ich definiere also durch den Test wie meine öffentliche Schnitstelle aussieht. Grundsätzlich führt TDD zu gutem Klassenentwurf. Vorraussetzung ist natürlich zu wissen was man wirklich benötigt - sollten sich neue Businessregeln mit der Zeit ergeben, bereits existierende ändern oder vorhandene nicht mehr benötigt werden, so führt TDD zu unnötigem Mehraufwand. Es wirkt jedoch der häufig mangelnden Abdeckung und Qualität an Tests entgegen.

## Grundprinzipien

### Red-Green-Refactor

Nach einem Unit-Test wird die zu testende Implementierung programmiert. Darauf folgt wieder ein weiterer Test mit Implementierung. Diese *Schleife* besteht aus drei Hauptteilen, dem Red-Green-Refactor:

- Red: der Test wird geschrieben und schlägt fehlt, da keine Implementierung vorhanden
- Green: korrekte Implementierung wird umgesetzt, damit der Test erfolgreich ist
- Refactor: Standard-Refactoring (Duplikate, Abhängigkeiten, Clean Code, ...) wird durchgeführt - nach jeder Änderung muss der Test durchlaufen werden

### Code Coverage

Bei der Code Coverage handelt es sich um ein "test-everything" Prinzip. Es sollte jeder Code-Pfad getestet werden. Das bedeutet jede If-Else Verzweigung, jede Exception und Fehlermeldung, usw.

### Testabdeckung

Die Testabdeckung besagt, das der Umfang an Tests zwar mehr aber nie weniger werden darf. Somit wird sichergestellt, dass Softwaretests nicht vernachlässig werden.

### Benennung von Tests

Tests sollten recht detailliert benannt werden, damit aus dem Namen alle wichtigen Informationen herausgelesen werden können und keine Verwechslungen entstehen. Ein möglicher Namensaufbau könnte sein: `wasMoechteIchTesten_wasGebeIchEinOderMit_wasSollteHerauskommen`

### Given-When-Then

Mit Hilfe dieser Stichworte *kann* ein Test strukturiert werden. Er sollte immer durch die folgenden Teilen aufgebaut sein:

- Given: was ist gegeben
- When: wenn diese Opteraion geschieht
- Then: dann soll das dabei herauskommen

### Weiteres (Mitschrift 09.03.2022)

Ein Mock muss injiziert werden können (zB in Spring Boot, @Autowired am Konstruktor nicht am Datenfeld).

Bei den Tests sollte die selbe Verzeichnisstruktur aufweisen wie die Implementierungen!

Ein Test gibt selten etwas zurück oder hat Parameter.

Man kann auch eine Basisstrktur für Tests festlegen.

TestFactory

## Testarten

Man unterscheidet die Arten zu Testen nach den Komponenten/Programmteilen die getestet werden.

### Unit-Tests

Ein Unit-Test testet einen abgekapselten Teil des Programms, eine sogenannte **Unit**, wobei dessen Größe oder Umfang nicht definiert ist, es sich jedoch standardmäßig um eine kleine, wenig komplexe Einheit handelt, die mit wenigen Testfällen vollständig überprüft werden kann. Es kann sich dabei um Klassen, Methoden, Layer, usw. handeln. Wichtig ist, dass der Test keine Abhängigkeiten (mit anderen Units) aufweist. Es soll eben nur die Funktion der zu testenden Unit getestet werden! Unit-Tests treten daher auch recht häufg auf.

#### Mock (Mocking)

Bei einem Mock handelt es sich um ein Dummy Objekt, das Dummy Werte bereitstellt. Es liefert Dummywerte mit denen ein Test arbeiten kann.

#### Sociable & Solitary

Unter einem **Solitary** Unit-Test versteht man eine übliche Unit-Testklasse, deren Zweck nur das testen dieser Unit ist (alles andere wird durch Mocking bereitgestellt).

Ein **Sociable** Unit-Test verwendet hingegen konkrete Abhängigkeiten für den Test (auch Komponententest genannt).
Die Frage stellt sich hier wieder, für was nun eine Unit steht.

### Integrationstests

Bei einem Integrationstests wird nun die Kooperation zweier Systeme/Units getestet. Es wird geprüft ob das Zusammenspiel untereinander funktioniert. Für einen erfolgreichen Test ist vorher sicherzustellen, dass die Einzelteile auch in sich geschlossen funktionieren (Unit-Tests). Integrationstests werden eher reduzierter eingesetzt.

### UI-Tests

Ein UI-Test befasst sich mit den Programmteilen die keine Logik enthalten. Es wird einzig und allein das User Interface, häufig auch automatisiert getestet.

(Interessanter Artikel: https://bit.ly/3IgyqaI)

### Systemtests bzw. End-To-End Tests

Werden oft mit UI-Tests gleichgestellt bzw. UI-Tests genannt, was aber nicht ganz richtig ist. Hier wird nämlich das komplette System (Fullstack, also Datenbank, Backend mit jeglicher Logik, Frontend mit UI, usw.) druchgetestet. Nur weil die UI ein teil hiervon ist, handelt es sich aber nicht um einen UI-Test! Es werden also sowohl funktionale und nicht-funktionale Anforderungen getestet. Hiervon existieren meistens nur sehr wenige (häufig nur einer), da sie extrem Umfangreich sind. Daher ist auch die Code-Coverage sehr schwierig. 

### Akzeptanztests

Ein Akzeptanz- oder Abnahmetest überprüft ob das komplette System/Programm aus der Sicht des Benutzers funktioniert (und dieser die Software damit akzeptiert). Beispielsweise eine interne ungewollte Abhängigkeit kann damit nicht getestet werden, sollte das System dadurch trotzdem funktionieren. Häufg wird eine soche Überprüfung mithilfe von Beta-Tests durchgeführt.

# Testpyramide

Die Testpyramide soll die Aufteilung der Tests in einem System zeigen.

1. UI-Tests / Akzeptanztests
2. Systemtests / Integrationstests
3. Unit-Tests

Oftmals wird auch eine weitere Ebene zwischen den Akzeptanz- und Integrationstests eingefühgt, die dann die Systemtests enthält.

1. UI-Tests / Akzeptanztests
2. Systemtests
3. Integrationstests
4. Unit-Tests

Die Pyramide zeigt je nach Größe der Fläche, wie einfach und schnell die Tests durchzuführen sind und wie häufig sie vorkommen. Desto komplexer und schwieriger die Tests sind, je kleiner die Fläche.

(Genauere Beschreibung: https://bit.ly/3wfv6dJ)

# JUnit

JUnit ist ein Java Testframework, mit Hauptaugenmerk auf automatisierten (und) Unit-Tests. Im Grunde kenn JUnit nur zwei Zustände: Rot (der Test gelingt nicht) oder Grün (der Test gelingt). Ein Fehlerhafter Test wird über eine Exception behandelt, unterscheidet sich aber dadurch von einem Error, dass er erwartet wird.

JUnit verwendet **Assertions** (=Testmethoden) und **Assumptions**(=Vorbedingungen) um Tests durchzuführen.

Mithilfe von JUnit @-Annotationen können Methoden für den Testvorgang konfiguriert werden. Wichtige sind:

- `@Test` - kennzeichnet einen Test (bzw. eine Testmethode)
- `@BeforeAll` - einmal vor einem Testlauf ausgeführt
- `@BeforeEach` - vor jedem einzelnen Test ausgeführt
- `@AfterAll` - gleiches Prinzip nur dannach
- `@AfterEach` - gleiches Prinzip nur dannach

(https://de.wikipedia.org/wiki/JUnit und https://junit.org/junit5/)

## Maven

JUnit kann mittels Dependency in der `pom.xml`, einem Maven Projekt hinzugefügt werden. Durch den *Maven Lifecycle **test*** können die automatisch ausgeführt werden (kann zB die CI-Pipeline auf GitHub auch).

## Kent Beck

Kent Beck ist der Erfinder des Frameworks und amerikanischer Softwareentwickler. Außerdem hat er mehrere Bücher über Softwareentwicklung, unter anderem mit dem Thema TDD verfasst (Red-Green-Refactor).

# Mockito (Mocking-Bibliothek)

Mockito ist eine Java-Bibliothek, die mithilfe von Mocking Dummy-Objekte für Unit-Tests bereitstellen kann. Der Grundgedanke, dass nur ein Programmteil / eine Unit ohne Abhängigkeiten getestet wird, wird so unterstützt.

(https://de.wikipedia.org/wiki/Mockito und https://site.mockito.org/)