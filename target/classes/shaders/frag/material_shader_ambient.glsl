#version 450

const int MAX_LIGHTS_COUNT = 64;

struct AmbientLight{
    vec3 ambient;
};

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    vec3 emissive;
    float density;
    float specular_exponent;
    float transparency;
    int illum;
};

uniform Material material = {
    vec3(0, 0, 1),
    vec3(1, 1, 1),
    vec3(1, 1, 1),
    vec3(1, 1, 1),
    1.0,
    1.0,
    1.0,
    1
};

uniform AmbientLight ambientLight;

in vec3 aPosition;

layout(location = 0) out vec4 gl_FragColor;

void main() {
    gl_FragColor = vec4(ambient * material.ambient, 1.0);
}
