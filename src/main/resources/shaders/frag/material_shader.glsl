#version 450

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

struct Light {
    vec3 position;
    vec3 direction;
    vec3 color;
};

in vec3 aPosition;
in vec3 aNormal;
in vec3 lightPos;

out vec4 gl_FragColor;

uniform Material material = {
vec3(1, 1, 1),
vec3(1, 1, 1),
vec3(1, 1, 1),
vec3(1, 1, 1),
1.0,
1.0,
1.0,
1
};

uniform Light light = {
    vec3(0, 2, 0),
    vec3(0, -1, 1),
    vec3(1, 0, 0)
};

void main() {
    float distance = length(lightPos - aPosition);
    float attenuation = 1.0 / (1.0 + 0.14     * distance +
    0.07 * (distance * distance));

    // ambient
    float ambientStrength = 0.2;
    vec3 ambient = ambientStrength * material.ambient;

    // diffuse
    vec3 normal = normalize(aNormal);
    vec3 lightToObject = normalize(lightPos - aPosition);
    float lightToObjectAngle = max(dot(normal, lightToObject), 0.0);
    vec3 diffuse = lightToObjectAngle * material.diffuse;

    // specular
    float specularStrength = 0.3;
    vec3 eye = normalize(-aPosition);// eye is the vector eye -> object
    vec3 r = reflect(-lightToObject, normal);// r is the reflection of vector object -> light
    float reflectionToEye = pow(max(dot(eye, r), 0.0), 32);
    vec3 specular = specularStrength * reflectionToEye * material.specular;

    vec3 res = (ambient + diffuse + specular) * light.color;
    gl_FragColor = vec4(res, 1.0);
}
