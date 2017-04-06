# $1 filename
# $2 uri
# $3 json string
# $4 http:0/https:1
# $5 statusSwitch 0:off 1:on
# $6 statusValue
if [ $4 -eq 0 ]
then
    if [ $5 -eq 0 ]
    then
      echo "location $2{" >>/etc/nginx/conf.d/mock/http/$1.conf
      echo "   proxy_pass http://localhost/$1.json;" >>/etc/nginx/conf.d/mock/http/$1.conf
      echo "}" >>/etc/nginx/conf.d/mock/http/$1.conf
    elif [ $5 -eq 1 ]
    then
      echo "location $2{" >>/etc/nginx/conf.d/mock/http/$1.conf
      echo "   return $6;" >>/etc/nginx/conf.d/mock/http/$1.conf
      echo "}" >>/etc/nginx/conf.d/mock/http/$1.conf
    fi


elif [ $4 -eq 1 ]
then
    if [ $5 -eq 0 ]
    then
      echo "location $2{" >>/etc/nginx/conf.d/mock/https/$1.conf
      echo "   proxy_pass http://localhost/$1.json;" >>/etc/nginx/conf.d/mock/https/$1.conf
      echo "}" >>/etc/nginx/conf.d/mock/https/$1.conf
    elif [ $5 -eq 1 ]
    then
      echo "location $2{" >>/etc/nginx/conf.d/mock/https/$1.conf
      echo "   return $6;" >>/etc/nginx/conf.d/mock/https/$1.conf
      echo "}" >>/etc/nginx/conf.d/mock/https/$1.conf
    fi


fi


echo "$3" >>/usr/share/nginx/html/$1.json
