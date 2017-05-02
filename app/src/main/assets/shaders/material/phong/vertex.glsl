
uniform mat3 normalMatrix;
uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;
uniform mat4 uMVPMatrix;
attribute vec3 position;
attribute vec3 normal;

#ifdef USE_MAP
    attribute vec2 uv;
    varying vec2 vUv;
#endif

varying vec3 vNormal;
varying vec4 vFragPos;
varying vec3 vEyeDir;

uniform vec3 cameraPosition;
varying vec3 vCameraPosition;




void main(){
    #ifdef USE_MAP
        vUv = uv;
    #endif

    vNormal = normalize(normalMatrix * normal);

    vFragPos = modelViewMatrix * vec4(position,1.0);

    vCameraPosition = cameraPosition;

    vEyeDir = normalize(- vFragPos.xyz);

    gl_Position = projectionMatrix * vFragPos;

}
