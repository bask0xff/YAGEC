# YAGEC
YAGEC - Yet Another Game Engine (Canvas version) for developing 2D games for Android mobile devices, such as 3-in-a-row, 2048, chess, word games, and others.

How to 
More info see here: https://jitpack.io/#bask0xff/YAGEC

Docs in Russian: https://familiar-september-058.notion.site/YAGEC-3ab2de22563d4e72a7f7fd6896931388

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  OR in settings.gradle:
  
	  dependencyResolutionManagement {
	    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	    repositories {
		google()
		mavenCentral()
		maven { url 'https://jitpack.io' }
	    }
	}
  
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.bask0xff:YAGEC:-SNAPSHOT'
	}
  
