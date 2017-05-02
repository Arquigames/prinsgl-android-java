uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;
attribute vec3 position;

/*
#ifdef USE_NORMAL
    uniform mat3 normalMatrix;
    attribute vec3 normal;
    varying vec3 vNormal;
#endif
*/

#ifdef USE_MAP
    attribute vec2 uv;
    varying vec2 vUv;
#endif

void main(){
    #ifdef USE_MAP
        vUv = uv;
    #endif

    /*
    #if defined(USE_NORMAL)
        vNormal = normalize(normalMatrix * normal);
    #endif
    */
    gl_Position = projectionMatrix * modelViewMatrix * vec4(position,1.0);

}
