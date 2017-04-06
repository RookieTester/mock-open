#$1 domain
sed -i "s/server_name/server_name  $1/g" default.conf
