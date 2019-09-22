import requests
import os
import sys

# api-endpoint 
URL = os.environ.get('DYNATRACE_URL')
TOKEN = os.environ.get('DYNATRACE_API_TOKEN')

if URL is None:
    print('You must have a DYNATRACE_URL environmental variable set')
    sys.exit()
if TOKEN is None:
    print('You must have a DYNATRACE_API_TOKEN environmental variable set')
    sys.exit()

URL = URL + '/rest/v2/metrics/series'
selector = 'com.dynatrace.builtin:host.cpu.(user,idle,other,steal,system)'

# params
PARAMS = {'selector' : selector}
  
# sending get request and saving the response as response object 
r = requests.get(url = URL, params = PARAMS) 
  
# extracting data in json format 
data = r.json() 
  
  