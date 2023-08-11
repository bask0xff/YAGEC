# YAGEC

YAGEC - Yet Another Game Engine (Canvas version) is for developing 2D games for Android mobile devices, such as match-three, 2048, chess, word games, and others.

For more information, please see here: https://jitpack.io/#bask0xff/YAGEC

Documentation is available in Russian at: https://familiar-september-058.notion.site/YAGEC-3ab2de22563d4e72a7f7fd6896931388

To include this Git project in your build:

Step 1. Add the JitPack repository to your build file.

Add the following at the end of your root build.gradle file's repositories section:

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
  
