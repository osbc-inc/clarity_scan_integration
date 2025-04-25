Prerequisite
------------------------------------------------
### zip install
- This tool needs a 'zip' utility in your system.
  + Note: Install or use v3.0 or higher to create over 2GB zip file  
  + Windows: 
    - 'zip.exe' downoald url: http://stahlworks.com/dev/index.php?tool=zipunzip
    - Need to set path of `zip.exe` in your system environment    
  + Linux:
 ```bash
 $ yum install zip
 or
 $ apt-get install zip
 ``` 

### Java JDK
- This tool is tested with Open JDK 1.8


Usage
------------------------------------------------

### To run this tool
```bash
$ java -jar class [args....]

e.g)
$ java -jar clarity_scan_integration.jar --protocol http --address clarity.osbc.co.kr --email user@osbc.co.kr --password userpassword --project project_name --version version_name --targetpath /set/a/target_path --filename filename_to_be.zip --excludepath /exclude/path1/*,/exclude/path2/*,*.txt
```

--protocol: Clarity web interface protocol              
--address: Clarity address
--email: user email
--password: user password
--targetpath: Full path including source code to be analyzed in FossID server  
(optional)--excludepath: Explicitly exclude the specified files. Please, refer to the `-x` or `--exclude` parameter on the `https://linux.die.net/man/1/zip` page