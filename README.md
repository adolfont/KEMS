2009-10-01
This is the README file for KEMS, a KE-based multi-strategy tableau prover.

More info about KEMS can be found in http://adolfoneto.wikidot.com/KEMS

Publications:
- Serra-Seca-Neto, Adolfo G. Multi-Stratategy Tableau Prover, PhD. Thesis: http://www.teses.usp.br/teses/disponiveis/45/45134/tde-04052007-175943/en.php
- Serra-Seca-Neto, Adolfo G; Finger, Marcelo. Implementing a Multi-Strategy Theorem Prover. http://www.ime.usp.br/~adolfo/trabalhos/enia2005.pdf
- Serra-Seca-Neto, Adolfo G; Finger, Marcelo. KEMS - A Multi-Strategy Tableau Prover. http://www.dainf.ct.utfpr.edu.br/~adolfo/publications/2008/wtdia_2008_neto_finger.pdf
- Serra-Seca-Neto, Adolfo G; Finger, Marcelo. KEMS - A Multi-Strategy Tableau Prover. http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.495.2771&rep=rep1&type=pdf

Requirements

1. Eclipse 3.4.2 with AspectJ Development Tools (AJDT) plugin installed
2. Sun's Java SDK (it does not work with other Java development kits)
	java version "1.6.0_16"
	Java(TM) SE Runtime Environment (build 1.6.0_16-b01)
	Java HotSpot(TM) Client VM (build 14.2-b01, mixed mode, sharing)


Installation instructions

From git

1. Install git
2. Issue the command

git clone git://github.com/adolfont/KEMS.git

3. Open Eclipse
 3.1 Open a new workspace
 3.2 Choose "File"
 3.3 Choose "Import"
 3.4 Click on the triangle at the left side of "General"
 3.5 Click on "Existing Projects into Workspace" 
 3.6 Click on "Next"
 3.4 Click on "Select root directory"
 3.8 Browse to the path where KEMS workspaces were cloned
 3.9 Click on "OK"
 3.6 Click on Finish


 3.4 Click on "Select root directory"
 

 3.6 Click on Finish

From KEMS home page

1. No need to have git installed
2. Go to 

http://www.dainf.ct.utfpr.edu.br/~adolfo/KEMS

and download KEMS's latest version with source code.

3. Open Eclipse
 3.1 Open a new workspace
 3.2 Choose "File"
 3.3 Choose "Import"
 3.4 Click on the triangle at the left side of "General"
 3.5 Click on "Existing Projects into Workspace" 
 3.6 Click on "Next"
 3.4 Click on "Select archive file"
 3.8 Browse to the path where KEMS*.zip is located
 3.9 Choose KEMS*.zip
 3.10 Click on "OK"
     // the names of kems subprojects must appear
 3.11 Click on Finish

Turn Weaving Service on?
Should AJDT's weaving service be enabled? (Requires restart)
NO!!!

Git commands


- After changes
git commit -a -m 'Message'
git push origin master

- To get new stuff
git fetch

- What exactly does this do?
git merge origin/master

- Add new files
At the project root folder
git add .



- (for the owner) To download updates from collaborators
  git pull git://github.com/adolfont/KEMS.git experimental
